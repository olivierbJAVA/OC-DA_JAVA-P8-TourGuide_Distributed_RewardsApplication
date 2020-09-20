package tourGuide.tracker;

import java.util.List;
import java.util.concurrent.*;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

public class Tracker extends Thread {
	private Logger logger = LoggerFactory.getLogger(Tracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(1);//initial = 5
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	//private final ExecutorService executorService = Executors.newFixedThreadPool(10);
	private final TourGuideService tourGuideService;
	private final RewardsService rewardsService;
	private boolean stop = false;

	private CompletableFuture<String> completableFuture;

	public Tracker(TourGuideService tourGuideService, RewardsService rewardsService) {
		this.tourGuideService = tourGuideService;
		this.rewardsService = rewardsService;

		//CompletableFuture.supplyAsync(this);

		executorService.submit(this);
	}
	
	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}
	
	@Override
	public void run() {
		StopWatch stopWatch = new StopWatch();
		while(true) {
			if(Thread.currentThread().isInterrupted() || stop) {
				logger.debug("Tracker stopping");
				break;
			}
			
			List<User> users = tourGuideService.getAllUsers();
			logger.debug("Begin Tracker. Tracking " + users.size() + " users.");
			stopWatch.start();

			//VERSION_INITIALE
			//users.forEach(u -> tourGuideService.trackUserLocation(u));


			// DEBUT_ESSAIS
			/*
			// Solution_1
			users.forEach(u -> tourGuideService.trackUserLocation(u));
			logger.debug("Calculate rewards.");
			users.forEach(u -> rewardsService.calculateRewards(u));
			*/

			/*
			// Solution_2
			CompletableFuture.runAsync(
					new Runnable (){
							@Override
							public void run() {
								users.forEach(u -> tourGuideService.trackUserLocation(u));
					}}).thenAccept(
					new Consumer<Void>() {
						@Override
						public void accept(Void unused) {
							users.forEach(u -> rewardsService.calculateRewards(u));
						}
					});
			*/

			/*
			// Solution_3
			CompletableFuture cf = new CompletableFuture();
					cf.runAsync(
					() -> users.forEach(tourGuideService::trackUserLocation)).thenAccept(
					unused -> users.forEach(rewardsService::calculateRewards));
			*/
			/*
			// Solution_3_Bis
			CompletableFuture cf = CompletableFuture
					.runAsync( () -> users.forEach(tourGuideService::trackUserLocation), executorService)
					.thenAccept( unused -> users.forEach(rewardsService::calculateRewards) );
			*/
			/*
			// Solution_3_Ter
			CompletableFuture cf = CompletableFuture
					.runAsync( () -> users.forEach(tourGuideService::trackUserLocation))
					.thenAccept( unused -> users.forEach(rewardsService::calculateRewards) );

			while ( !cf.isDone() ) {
				System.out.println("Wait");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				logger.debug("Previous completableFuture.get");
				cf.get();
				logger.debug("After completableFuture.get");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			*/

			/*
			// Solution_4TOther
			users.forEach((user)->CompletableFuture.runAsync(
					new Runnable (){
						@Override
						public void run() {
							tourGuideService.trackUserLocation(user);
						}}).thenAccept(
					new Consumer<Void>() {
						@Override
						public void accept(Void unused) {
							rewardsService.calculateRewards(user);
						}
					}));
			*/
			/*
			// Solution_4
			for (User user:users){
				CompletableFuture.runAsync(()->tourGuideService.trackUserLocation(user))
						.thenAccept(unused->rewardsService.calculateRewards(user));
			}
			*/
			/*
			// Solution_4Bis
			for (User user:users){
				CompletableFuture.supplyAsync(()->tourGuideService.trackUserLocation(user))
						.thenAccept(unused->rewardsService.calculateRewards(user));
			}
			*/
			/*
			// Solution_4Ter_OK_SANS-GET
			users.forEach((user)-> {
					CompletableFuture
							.runAsync(()->tourGuideService.trackUserLocation(user), executorService)
							.thenAccept(unused->rewardsService.calculateRewards(user));
			});
			*/
			/*
			// Solution_4Ter_OK_AVEC-GET
			users.forEach((user)-> {
				try {
					CompletableFuture
							.runAsync(()->tourGuideService.trackUserLocation(user), executorService)
							.thenAccept(unused->rewardsService.calculateRewards(user)).get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
			*/
			// FIN_ESSAIS


			// DEBUT_VERSION_AMELIOREE
			ForkJoinPool forkJoinPool = new ForkJoinPool(100);
			//final ForkJoinPool test = new ForkJoinPool(1,	ForkJoinPool.defaultForkJoinWorkerThreadFactory, null,true);

			users.forEach((user)-> {
				CompletableFuture
						.runAsync(()->tourGuideService.trackUserLocation(user), forkJoinPool)
						.thenAccept(unused->rewardsService.calculateRewards(user));
			});

			//Optional : in case you want to wait for the completion of track users and calculate rewards before Tracker sleeping
			forkJoinPool.awaitQuiescence(10,TimeUnit.MINUTES);

			//ForkJoinPool.commonPool().awaitQuiescence(10,TimeUnit.MINUTES);

			// FIN_VERSION_AMELIOREE

			stopWatch.stop();
			logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
			stopWatch.reset();
			try {
				logger.debug("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				break;
			}

			/*
			boolean result = forkJoinPool.awaitQuiescence(1,TimeUnit.MINUTES);
			if(result) {
				logger.debug("Tracking done in trackingPollingInterval");
			} else {
				logger.debug("Warning : Tracking last more than trackingPollingInterval ");
			}
			*/
		}
		
	}
}
