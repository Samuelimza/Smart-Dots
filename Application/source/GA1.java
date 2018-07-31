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

public class GA1 extends PApplet {

int n = 1000;
Dot target;
Population present;
PFont f;

public void setup(){
  background(200);
  frameRate(100);
  
  target = new Dot(width / 2, 50, 5, true);
  present = new Population(width / 2, height - 50, n, true);
  f = createFont("Arial", 16, true);
}

public void draw(){
  background(51);
  textFont(f, 30);
  stroke(4);
  fill(187, 252, 184, 150);/*(251, 214, 133, 100);*/
  text("Gen: " + present.gen, 10, 40);
  if(present.leastStep == 1000){
    text("Path: Not Found", 10, 80);
  }else{
    text("Path: Found", 10, 80);
    text("Least Steps: " + present.leastStep, 10, 1200);
  }
  stroke(5);
  fill(46, 153, 176);
  rect(0, 300, width - 300, 10);
  rect(300, 400, width - 300, 10);
  rect(0, 500, width - 300, 10);
  target.display();
  
  present.update();
  present.display();
  
  saveFrame("frames/####.tga");
  if(present.allDead()){
    present.calcMaxFitness();
    present.reproduce();
    present.mutate();
  }
}
class DNA{
  PVector[] list;
  int n;
  float mutateChance = 0.01f;
  float cap;
  
  DNA(int n, float cap){
    this.n = n;
    this.cap = cap;
    list = new PVector[this.n];
    for(int i = 0; i < n; i++){
      list[i] = new PVector(random(-cap, cap), random(-cap, cap));
    }
  }
  
  DNA(DNA dna){
    this.n = dna.n;
    this.cap = dna.cap;
    this.mutateChance = dna.mutateChance;
    list = new PVector[this.n];
    for(int i = 0; i < this.n; i++){
      list[i] = new PVector(dna.list[i].x, dna.list[i].y);
    }
  }
  public void mutate(){
    for(int i = 0; i < n; i++){
      if(random(1) < mutateChance){
        list[i] = new PVector(random(-cap, cap), random(-cap, cap));
      }
    }
  }
}
class Dot {
  boolean alive = true;
  boolean reached = false;
  boolean isBest = false;
  PVector pos;
  PVector vel;
  PVector acc;
  float r;
  DNA dna;
  int dnaLength = 1000;
  float cap = 0.5f;
  int i = 0;
  boolean isTarget = false;
  float fitness;
    
  Dot(Dot d){
    pos = new PVector(width / 2, height - 50);
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
    r = d.r;
    dna = new DNA(d.dna);
    dnaLength = d.dnaLength;
    cap = d.cap;
    isTarget = false;
  }
  
  Dot(int x, int  y, float r, boolean isTarget){
    this.isTarget = isTarget;
    dna = new DNA(dnaLength, cap);
    pos = new PVector(x, y);
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
    this.r = r;
  }
  
  Dot(int x, int y, float r, boolean isTarget, DNA passedDNA){
    this.isTarget = isTarget;
    dna = new DNA(passedDNA);
    pos = new PVector(x, y);
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
    this.r = r;
  }
  
  public void update(){
    if(pos.x > width - r || pos.x < r || pos.y > height - r || pos.y < r){
      die();
    }else if(this.isTouching(target)){
      this.reached = true;
      die();
    }else if(touchingObstacle()){
      die();
    }
    if(i < dnaLength){ 
      acc.add(dna.list[i++]);
    }else{
      die();
    }
    vel.add(acc);
    vel.limit(5);
    pos.add(vel);
    acc.setMag(0);
  }
  
  public boolean touchingObstacle(){
    if(pos.y > 300 && pos.y < 310 && pos.x > 0 && pos.x < width - 300){
      return true;
    }else if(pos.y > 400 && pos.y < 410 && pos.x > 300 && pos.x < width){
      return true;
    }else if(pos.y > 500 && pos.y < 510 && pos.x > 0 && pos.x < width - 300){
      return true;
    }
    return false;
  }
  
  public float dist(Dot d){
    return this.pos.dist(d.pos);
  }
  
  public void die(){
    calcFitness();
    alive = false;
  }
  
  public boolean isTouching(Dot d){
    if(this.dist(d) < this.r + d.r){
      return true;
    }else{
      return false;
    }
  }
  
  public void calcFitness(){
    if(this.reached){
      fitness = 0.01f + (1.0f / (i * i));
    }else{
      fitness = 1 / (this.dist(target) * this.dist(target));
    }
  }
  
  public void applyForce(PVector p){
    acc.add(p);
  }
  
  public void display(){
    fill(255, 100);
    if(isBest){
      fill(255, 46, 76);
    }
    if(isTarget){
      fill(252, 215, 127);
    }
    stroke(100);
    ellipse(pos.x, pos.y, 2 * r, 2 * r);
  }
}
class Population{
  Dot[] dots;
  float maxFitness = 1000;
  int maxFitI = 0;
  int x, y, n, leastStep = 1000;
  int gen = 0;
  
  Population(int x, int y, int n, boolean random){
    this.x = x; this.y = y; this.n = n;
    dots = new Dot[n];
    if(random){
      for(int i = 0; i < n; i++){
        dots[i] = new Dot(x, y, 5, false);
      }
    }
  }
  
  public boolean allDead(){
    for(Dot d : dots){
      if(d.alive){
        return false;
      }
    }
    return true;
  }
  
  public void calcMaxFitness(){
    maxFitness = 0;
    for(int i = 0; i < this.n; i++){
      if(dots[i].fitness > maxFitness){
        maxFitness = dots[i].fitness;
        maxFitI = i;
      }
    }
    if(dots[maxFitI].reached){
      leastStep = dots[maxFitI].i; 
    }
    println();
    print(maxFitness);
    println();
  }
  
  public Dot chooseParent(){
    float luckyNumber = random(maxFitness);
    float runningSum = 0;
    for(int i = 0; i < this.n; i++){
       runningSum += dots[i].fitness;
       if(runningSum >= luckyNumber){
         return dots[i];
       }
    }
    return null;
  }
  
  public void reproduce(){
    Dot[] next = new Dot[this.n];
    for(int i = 1; i < this.n; i++){
      DNA newDNA = new DNA(chooseParent().dna);
      next[i] = new Dot(this.x, this.y, 5, false, newDNA);
    }
    next[0] = new Dot(dots[maxFitI]);
    next[0].isBest = true;
    //next[1].isTarget = true;
    for(int i = 1; i < this.n; i++){
      dots = next.clone();
    }
  }
  
  public void mutate(){
    for(int i = 1; i < this.n; i++){
      dots[i].dna.mutate();
    }
    gen++;
  }
  
  public void display(){
    for(int i = 0; i < this.n; i++){
      dots[i].display();
    }
  }
   
  public void update(){
    for(int i = 0; i < this.n; i++){
      if(dots[i].i > leastStep){
        dots[i].die();
      }else if(dots[i].alive){
        dots[i].update();
      }
    }
  }
}
  public void settings() {  size(700, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GA1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
