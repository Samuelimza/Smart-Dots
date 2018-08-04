int n = 1000;
Dot target;
Population present;
PFont f;

void setup(){
  frameRate(100);
  size(700, 700);
  target = new Dot(width / 2, 50, 5, true);
  present = new Population(width / 2, height - 50, n, true);
  f = createFont("Arial", 16, true);
}

void draw(){
  background(51);
  print("Here");
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
  
  if(present.allDead()){
    present.calcMaxFitness();
    present.reproduce();
    present.mutate();
  }
}
