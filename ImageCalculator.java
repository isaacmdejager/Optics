// Isaac DeJager
// This program is for the PHYS Optics project.

//Consider two parallel mirrors facing each other. When an object is placed between them,
//an infinite number of images are produced. The objective of this project is to graft a
//formula that relates the number of images to the angle the second mirror makes relative 
//to the vertical.

//This program takes two inputs from the user. First, the user enters the distance between
//the center of the mirrors (the second mirror rotates across its central axis). The second
//input is the angle the second mirror makes with the vertical. Then, it calculates the XY
//coordinates of all images produced and stores them in a 2-D array as well as the number of
//images.
//The distance between the mirrors does not affect the number of images. However, it does change
//the XY coordinates of the mirrors.
//The mirrors/images are on an XY grid with the origin at the center of the vertical mirror.

//Constraints:
//The user must input an angle such that:
//0.0036deg <= theta < 90deg
//An angle too low will result in too many images for the compiler to handle.
//The angle cannot equal 90deg because when the complier calculates 1 / tan(90),
//it does not return 0, it returns a very small number, and this adds a fake image
//to the count.
//The user must enter a value greater than 0 for d.

import java.lang.*;
import java.util.*;

public class ImageCalculator {

   public static void main(String[] args) {
      
      //User input
      int n = 0;
      Scanner input = new Scanner(System.in);
      System.out.println("Input the distances between the center of the mirrors in cm:");
      System.out.print("d = ");
      double d = input.nextDouble();
      System.out.println("Input the angle of the second mirror in degrees:");
      System.out.print("theta = ");
      double theta = (input.nextDouble()) * Math.PI / 180;
      
      //This 2-D array holds all the XY coordinates for the images
      //The array can hold a maximum of 100,000 points
      double images[][] = new double[100000][2];
      
      //These variables hold the XY coordinates of the previous point so that the 
      //coordinates of the reflected point can be calucluated
      double xFrom = d / 2;
      double yFrom = 0;
      
      //The images are split into two sets. The first set is the images that result from
      //the first reflection being over the veritcal mirror. The second set is the set of
      //images that result from the first reflection being over the slanted mirror.
      
      //First set of images (initial reflection over vertical)
      while(true) {
         
         //If n is even, then the next reflection is over the vertical mirror
         if(n % 2 == 0) {
         
            //See attached documents for forumulas
            images[n][0] = (-1) * xFrom;
            images[n][1] = yFrom;
         
         }
         
         //If n is odd, the next reflection is over the slanted mirror
         else {
         
            //See attached documents for formulas
            images[n][0] = xReflectRight(xFrom, yFrom, d, theta);
            images[n][1] = yReflectRight(xFrom, yFrom, d, theta);
         
         }
         
         //Setting the previous point coordinates to the current point coordinates
         xFrom = images[n][0];
         yFrom = images[n][1];
         
         //Adding one to the number of images
         n++;
         
         //If the image cannot be reflected, break the loop
         //See attached documents for when an image cannot be reflected
         if(xFrom <= 0 && xFrom >= d - yFrom * Math.tan(theta) && yFrom >= (d - xFrom) / Math.tan(theta)) {
            break;
         }
      
      }
      
      //Reseting the variables
      xFrom = d / 2;
      yFrom = 0;
      
      //Creating a counter to determine which mirror the image is being reflected over
      int c = 0;
      
      while(true) {
         
         //If c is even, the image is reflecting over the slanted mirror
         if(c % 2 == 0) {

            images[n][0] = xReflectRight(xFrom, yFrom, d, theta);
            images[n][1] = yReflectRight(xFrom, yFrom, d, theta);
            
         }
         
         //If c is odd, the image is reflecting over the vertical mirror
         else {
            
            images[n][0] = (-1) * xFrom;
            images[n][1] = yFrom;
            
         }
         
         //Setting the previous point coordinates to the current point coordinates
         xFrom = images[n][0];
         yFrom = images[n][1];
         
         //Adding one to the number of images
         n++;
         c++;
         
         //Determining whether the image will be reflected
         if(xFrom <= 0 && xFrom >= d - yFrom * Math.tan(theta) && yFrom >= (d - xFrom) / Math.tan(theta)) {
            break;
         }
         
      }
      
      //Displaying the number of images
      System.out.println("The number of images: ");
      System.out.println("n = " + n);
      
      input.close();
   
   }
   
   public static double xReflectRight(double x, double y, double d, double theta) {
      
      return 2 * Math.cos(theta) * Math.cos(theta) * (d - y * Math.tan(theta) - x) + x;
      
   }
   
   public static double yReflectRight(double x, double y, double d, double theta) {
   
      return 2 * Math.cos(theta) * Math.sin(theta) * (d - y * Math.tan(theta) - x) + y;
   
   }

}