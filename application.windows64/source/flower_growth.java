import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class flower_growth extends PApplet {

class Flower{
  int numPoints = 90;
  float[] xCoord = new float[numPoints];
  float[] yCoord = new float[numPoints];
  float radius;
  float numDecay;
  float xloc;
  float yloc;
  int interact = 0;
  
  //list of the x and y positions of all the points drawn for each flower
  ArrayList<PVector> points = new ArrayList<PVector>();
  //list of the x and y positions of all the water points for each flower 
  ArrayList<PVector> water = new ArrayList<PVector>();
  
  //water counter for each flower
  int numWater = 0;
  
  //decay rates
  float poppyDRate = 0.000005f;
  float roseDRate = 0.00000005f;
  float bbDRate = 0.00000000000000000005f;
  float sunnyDRate = 0.00005f;

  Flower(){
  }
  
  //function to draw the seed (circle) using points
  public void sketch(float xcenter, float ycenter){
    xloc = xcenter;
    yloc = ycenter;
    for(int i = 0; i <numPoints; i++){
      radius = 10;
      xCoord[i] = (xloc)+sin(radians(4*i))*radius;
      yCoord[i] = (yloc)+cos(radians(4*i))*radius;
      PVector pos = new PVector(xCoord[i],yCoord[i]);
      points.add(pos); //store the x and y positions of every point drawn for each flower seed
      point(points.get(i).x, points.get(i).y);
    }
  }
  
  //poppy growth pattern function
  public void poppygrow(){
    for(int i = 0; i<numPoints;i++){
      stroke(77,38,81);
      xCoord[i] += random(-10,10);
      yCoord[i] += random(-10,10);
      PVector pos = new PVector(xCoord[i],yCoord[i]);
      points.add(pos); //store the x and y positions of every point drawn for each poppy
      point(xCoord[i], yCoord[i]);
    }
  }
    
  //rose growth pattern function
  public void rosegrow(){
    for(int i = 0; i<5;i++){
      stroke(248,82,120);
      xCoord[i] += random(-1,1);
      yCoord[i] += random(-1,1);
      PVector pos = new PVector(xCoord[i],yCoord[i]);
      points.add(pos); //store the x and y positions of every point drawn for each rose
      point(xCoord[i], yCoord[i]);
    }
  }
  
  //baby's breath growth pattern function
  public void bbgrow(){
    for(int i = 0; i<1;i++){
      stroke(253,240,207);
      xCoord[i] += random(-10,10);
      yCoord[i] += random(-10,10);
      PVector pos = new PVector(xCoord[i],yCoord[i]);
      points.add(pos); //store the x and y positions of every point drawn for each baby's breath flower
      point(xCoord[i], yCoord[i]);
    }
  }

  //test a different type of flower growth (sunflower) - function too heavy, slows down the program
  /* void sungrow(){
   for(int i = 0; i<numPoints;i++){
      stroke(240,133,41);
      xCoord[i] += random(cos(i)*3);
      yCoord[i] += random(sin(i)*3);
      PVector pos = new PVector(xCoord[i],yCoord[i]);
      points.add(pos);
      point(xCoord[i], yCoord[i]);
    }for(int j = 0; j<points.size();j++){
      for(int i = j; i < points.size(); i++){
        if(dist(points.get(j).x, points.get(j).y,points.get(i).x, points.get(i).y)<100){
          interact++;
        }
      }
      if (interact > 1000){
        stroke(253,240,207);
        point(points.get(j).x, points.get(j).y);
      }*/
      /*else if (interact > 1500){
        println("3");
        stroke(237,85,59);
        point(points.get(j).x, points.get(j).y);
      }*/
      /*else if (interact > 1000){
        println("2");
        stroke(236,120,99);
        point(points.get(j).x, points.get(j).y);
      }*/
      /*else if(interact > 500){
        stroke(254,198,96);
        point(points.get(j).x, points.get(j).y);
      }*/
     /* interact = 0;
    }
  }
  */
  
  //flower decay function (removing points by redrawing them in the same color as the background color)
  public void decay(float rate){
    numDecay = rate*points.size();
    int count = 0;
    while (count < numDecay ){
      stroke(56,21,42);
      int rdm = (int)random(0,points.size()-1);
      point(points.get(rdm).x, points.get(rdm).y);
      points.remove(rdm); //delete the points from the stored array list of points
      count++;
    }
  }
}


Flower poppy;
Flower rose;
Flower bb;
Flower sunflower;
ArrayList<PVector> allwater;
ArrayList<Flower> allpoppies = new ArrayList<Flower>();
ArrayList<Flower> allroses = new ArrayList<Flower>();
ArrayList<Flower> allbbs = new ArrayList<Flower>();
ArrayList<Flower> allsunnys = new ArrayList<Flower>();

float numDecay;
int Pnum = 0;
int Rnum = 0;
int Bnum = 0;
int Snum = 0;

int chooseFlower = 0; //counter to keep track of the type of flower chosen by the user
int grown = 0;
int count = 0;


public void setup(){
  
  background(56,21,42);
 
  allwater = new ArrayList<PVector>();
  
  //add 200 empty flowers of each type to its corresponding array list
  for(int i = 0; i < 200;i++){
    Flower poppy = new Flower();
    allpoppies.add(poppy);
  }
  for(int i = 0; i < 200;i++){
    Flower rose = new Flower();
    allroses.add(rose);
  }
   for(int i = 0; i < 200;i++){
    Flower bb = new Flower();
    allbbs.add(bb);
  }
  for(int i = 0; i < 200;i++){
    Flower sunflower = new Flower();
    allsunnys.add(sunflower);
  }
  
  //immediately start drawing 6 growing roses
  for(int j = 0; j<6;j++){
    stroke(56,21,42);
    allroses.get(Rnum).sketch(random(0,1000),random(0,1000));
    Rnum++;
  }
}

//choose flower type: user switches between poppy and baby's breath by pressing the up and down arrow keys
public void keyPressed(){
  if(keyPressed){
   if(key == CODED){
     if(keyCode == UP){
        chooseFlower++;
        if(chooseFlower == 2){
          chooseFlower = 0;
        }
      }else if (keyCode == DOWN){
        chooseFlower--;
        if(chooseFlower == -1){
          chooseFlower = 1;
        }
      }
    }
  }
}
  
public void draw(){
   
  //grow more roses: once a rose grows past 3000 points, another rose automatically grows
   for(int i = 0; i < Rnum;i++){
      allroses.get(i).rosegrow();
      if(allroses.get(i).points.size()>3000 && allroses.get(i).points.size()<3006){
        stroke(56,21,42);
        allroses.get(Rnum).sketch(random(0,1000),random(0,1000));
        Rnum++;
      }
    }
    
    //delete flowers: when the user presses SHIFT + Left mouse button on a flower
    if(keyPressed && key == CODED && keyCode == SHIFT && mousePressed && mouseButton == LEFT){
      //poppies
      for(int i = 0; i<Pnum;i++){
        for(int j = 0; j<allpoppies.get(i).points.size();j++){
          if(dist(allpoppies.get(i).points.get(j).x,allpoppies.get(i).points.get(j).y,mouseX,mouseY)<20){
            allpoppies.get(i).poppyDRate = 1;
            allpoppies.get(i).decay(allpoppies.get(i).poppyDRate);
            allpoppies.remove(i);
            Pnum--;
          }
        }
      }
      //baby's breath
      for(int i = 0; i<Bnum;i++){
        for(int j = allbbs.get(i).numPoints; j<allbbs.get(i).points.size();j++){
          if(dist(allbbs.get(i).points.get(j).x,allbbs.get(i).points.get(j).y,mouseX,mouseY)<20){
            allbbs.get(i).bbDRate = 1;
            allbbs.get(i).decay(allbbs.get(i).bbDRate);
            allbbs.remove(i);
            Bnum--;
          }
        }
      }
      //sunflowers
     /* for(int i = 0; i<Snum;i++){
        for(int j = 0; j<allsunnys.get(i).points.size();j++){
        if(dist(allsunnys.get(i).points.get(j).x,allsunnys.get(i).points.get(j).y,mouseX,mouseY)<20){
          allsunnys.get(i).sunnyDRate = 1;
          allsunnys.get(i).decay(allsunnys.get(i).sunnyDRate);
          allsunnys.remove(i);
          Snum--;
        }
      }
      }*/
      //roses
      for(int i = 0; i<Rnum;i++){
        for(int j = 0; j<allroses.get(i).points.size();j++){
          if(dist(allroses.get(i).points.get(j).x,allroses.get(i).points.get(j).y,mouseX,mouseY)<20){
            allroses.get(i).roseDRate = 1;
            allroses.get(i).decay(allroses.get(i).roseDRate);
            allroses.remove(i);
            Rnum--;
          }
        }
      }
    }
      
      
   //draw a seed: when the user presses the left mouse button
   else if(mousePressed && mouseButton == LEFT){
    if(chooseFlower == 0){//poppies
      stroke(77,38,81);
      allpoppies.get(Pnum).sketch(mouseX,mouseY);
      Pnum++;
    }else if (chooseFlower == 1){//baby's breath
      stroke(253,240,207);
      allbbs.get(Bnum).sketch(mouseX,mouseY);
      Bnum++;
    }/*else if (chooseFlower == 2){//sunflowers
      stroke(240,133,41);
      allsunnys.get(Snum).sketch(mouseX,mouseY);
      Snum++;
    }*/
  }
  
  //add water: when the user presses the right mouse button on a seed
  if(mousePressed && mouseButton == RIGHT){
    stroke(56,21,42);
    for(int i = 0; i<10;i++){
      float xloc = mouseX+sin(radians(36*i))*random(-30,30);
      float yloc = mouseY+cos(radians(36*i))*random(-30,30);
      PVector pos = new PVector(xloc,yloc);
      allwater.add(pos);
      point(xloc,yloc);  
    }
  }
 
  //count water particles
  for(int j = 0; j < Pnum;j++){//poppies
    allpoppies.get(j).numWater = 0;
    for(int i = 0; i < allwater.size();i++){
      if(dist(allwater.get(i).x,allwater.get(i).y,allpoppies.get(j).xloc,allpoppies.get(j).yloc)<allpoppies.get(j).radius+10){
        allpoppies.get(j).water.add(allwater.get(i));
        allpoppies.get(j).numWater++;
      }
    }
  }
    
  for(int j = 0; j < Bnum;j++){//baby's breath
    allbbs.get(j).numWater = 0;
    for(int i = 0; i < allwater.size();i++){
      if(dist(allwater.get(i).x,allwater.get(i).y,allbbs.get(j).xloc,allbbs.get(j).yloc)<allbbs.get(j).radius+20){
        allbbs.get(j).water.add(allwater.get(i));
        allbbs.get(j).numWater++;
      }
    }
  }
  /*for(int j = 0; j < Snum;j++){//sunflowers
      allsunnys.get(j).numWater = 0;
      for(int i = 0; i < allwater.size();i++){
       if(dist(allwater.get(i).x,allwater.get(i).y,allsunnys.get(j).xloc,allsunnys.get(j).yloc)<allsunnys.get(j).radius+10){
         allsunnys.get(j).water.add(allwater.get(i));
         allsunnys.get(j).numWater++;
       }
    }
 }
 */
 
  //water decay
  if(allwater.size()>0){
    numDecay = 0.0005f*allwater.size();
    int count = 0;
    while (count < numDecay){
      stroke(56,21,42);
      int rdm = (int)random(0,allwater.size()-1);
      point(allwater.get(rdm).x, allwater.get(rdm).y);
      allwater.remove(rdm);
      count++;
    }
  }
 
 //increase decay rate of other flowers: if there are more than 20 roses
 for(int i = 0;i<Pnum;i++){//poppies
   if(Pnum>0 && Rnum>20){ 
       allpoppies.get(i).poppyDRate = 0.005f;
   }else{
      allpoppies.get(i).poppyDRate = 0.000005f;
   }
 }
 for(int i = 0;i<Bnum;i++){//baby's breath
   if(Bnum>0 && Rnum>20){
       allbbs.get(i).bbDRate = 0.05f;
   }else{
      allbbs.get(i).poppyDRate = 0.00000000000000000005f;
   }
 }

 //growth and decay of all flowers (besides roses) based on amount of watering
  for(int i = 0; i < Pnum;i++){//poppies
    if(allpoppies.get(i).numWater > 100){
      allpoppies.get(i).poppygrow();
    }if(allpoppies.get(i).numWater <100 && allpoppies.get(i).points.size() > allpoppies.get(i).numPoints){
      allpoppies.get(i).decay(allpoppies.get(i).poppyDRate);
    }
  }
 
  for(int i = 0; i < Bnum;i++){//baby's breath
    if(allbbs.get(i).numWater > 200){
      allbbs.get(i).bbgrow();
    }if(allbbs.get(i).numWater <200 && allbbs.get(i).points.size() > allbbs.get(i).numPoints){
      allbbs.get(i).decay(allbbs.get(i).bbDRate);
    }
  }
  /*for(int i = 0; i < Snum;i++){//sunflowers
    if(allsunnys.get(i).numWater > 100){
      allsunnys.get(i).sungrow();
  }if(allsunnys.get(i).numWater <100 && allsunnys.get(i).points.size() > allsunnys.get(i).numPoints){
    allsunnys.get(i).decay(allsunnys.get(i).sunnyDRate);
  }
  }*/
   
  //reproduce: for each flower type besides roses, once there's a large amount of them, grow more 
  for(int i = 0; i<Pnum; i++){//poppies
    if(allpoppies.get(i).points.size()>7100 && allpoppies.get(i).points.size()<7161){
      stroke(77,38,81);
      allpoppies.get(Pnum).sketch(random(0,1000),random(0,1000));
      Pnum++;
      i=Pnum;
    }
  }
  for(int i = 0; i<Bnum; i++){//baby's breath
    if(allbbs.get(i).points.size()>800 && allbbs.get(i).points.size()<802){
      stroke(253,240,207);
      allbbs.get(Bnum).sketch(random(0,1000),random(0,1000));
      Bnum++;
      i=Bnum;
    }
  }
 /* for(int i = 0; i<Snum; i++){//sunflowers
    if(allsunnys.get(i).points.size()>400 && allsunnys.get(i).points.size()<402){
      stroke(240,133,41);
      allsunnys.get(Bnum).sketch(random(0,1000),random(0,1000));
      Snum++;
      i=Snum;
    }
  }*/
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#FFFFFF", "--stop-color=#CCCCCC", "flower_growth" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
