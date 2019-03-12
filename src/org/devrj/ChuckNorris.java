package org.devrj;

import java.util.Scanner;

public class ChuckNorris{
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        int numberOfFacts = 1;

        while(numberOfFacts > 0 && numberOfFacts <= 10) {

            System.out.println("How many Chuck Norris facts can you stand? (1-10)");

            //Get user input
            try {
                numberOfFacts = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException err) {
                System.out.println("Not a valid number. Displaying one fact");
                numberOfFacts = 1;
            }

            //Check input validity
            if(numberOfFacts < 0 || numberOfFacts > 10){
                System.out.println("Invalid number. Here's one fact -- ");
                numberOfFacts = 1;
            }

            //Display facts
            System.out.println("==================== CHUCK NORRIS ====================");
            for (int i = 0; i < numberOfFacts; i++) {
                new GetAJoke().thread.start();
            }


        }



    }

}
