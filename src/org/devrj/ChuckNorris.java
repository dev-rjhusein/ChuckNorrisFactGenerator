package org.devrj;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ChuckNorris{
    public static void main(String[] args){

        Logger logger = Logger.getLogger("Main Logger");

        Scanner scanner = new Scanner(System.in);
        int numberOfFacts = 1;

        while(numberOfFacts > 0 && numberOfFacts <= 10) {
            logger.info(">>> Thread Count: " + Thread.activeCount());

            System.out.println("How many Chuck Norris facts can you stand? Enter (1-10):");

            //Get user input
            try {
                numberOfFacts = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException err) {
                System.out.println("Not a valid number. Displaying one fact");
                numberOfFacts = 1;
            }

            //Check input validity
            if(numberOfFacts < 0 || numberOfFacts > 10){
                System.out.println("Invalid number. Here's ONE fact");
                numberOfFacts = 1;
            }

            //Display facts
            System.out.println("==================== CHUCK NORRIS ====================");

            CountDownLatch countDown = new CountDownLatch(numberOfFacts);

            for (int i = 0; i < numberOfFacts; i++) {
                new GetAJoke(countDown).thread.start();
            }

            //Wait until all threads have executed before looping
            try{
                countDown.await();
            }catch(InterruptedException err){
                logger.warning(">>> Thread error");
                throw new RuntimeException("Program Shutdown Unexpectedly");
            }

            System.out.println("======================================================\n");

        }



    }

}
