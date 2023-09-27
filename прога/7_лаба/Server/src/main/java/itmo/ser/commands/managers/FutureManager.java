package itmo.ser.commands.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class FutureManager {
    private static final Logger logger = Logger.getLogger(FutureManager.class.getName());
        private static final Collection<Future<ConnectionManagerPool>> cachedThreadPoolFutures = new ArrayList<>();

        public static void addNewCachedThreadPoolFuture(Future<ConnectionManagerPool> future) {
            cachedThreadPoolFutures.add(future);
        }

            public static void checkAllFutures(){
                if(!cachedThreadPoolFutures.isEmpty()) {
                    cachedThreadPoolFutures.forEach(s -> logger.info(s.toString()));
                }
                cachedThreadPoolFutures.stream()
                        .filter(Future::isDone)
                        .forEach(f -> {
                            try {
                                ConnectionManager.submitNewResponse(f.get());

                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        });
                cachedThreadPoolFutures.removeIf(Future::isDone);
                logger.info("поток удален");
            }
    }


