int n = 1000;
Dot target;
Population present;
Population children;

void setup(){
  background(200);
  frameRate(100);
  size(700, 700);
  target = new Dot(width / 2, 50, 5, true);
  present = new Population(width / 2, height - 50, n, true);
}

void draw(){
  background(100);
  stroke(5);
  fill(0, 0, 255);
  rect(0, 200, width - 200, 10);
  rect(200, 500, width - 200, 10);
  target.display();
  
  present.update();
  present.display();
  
  if(present.allDead()){
    save("hello.png");
    present.calcMaxFitness();
    present.reproduce();
    present.mutate();
  }
}
