// Joaquin Solla Vazquez
// Lucas Campos Camiña

mtype = {red, yellow, green};
mtype semV = red;
mtype semH = red;

proctype V(){
  do
    :: atomic{
      semH==red;
      semV=green;
    }
    printf("V: GREEN\n");
    semV=yellow;
    printf("V: YELLOW\n");
    atomic{
      semV=red;
      printf("V: RED\n");
      printf("------\n");
    }
  od
}

proctype H(){
  do
    :: atomic{
      semV==red;
      semH=green;
    }
    printf("H: GREEN\n");
    semH=yellow;
    printf("H: YELLOW\n");
    atomic{
      semH=red;
      printf("H: RED\n");
      printf("------\n");
    }
  od
}

init {
  printf("V: RED\n");
  printf("H: RED\n");
  run V();
  run H();
}
