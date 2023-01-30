// Joaquin Solla Vazquez
// Lucas Campos Camiña

mtype = {red, yellow, green};
mtype semV = red;
mtype semH = red;
mtype semV_clon = red;
mtype semH_clon = red;

// 0: Nobody was green yet (initial state)
// 1: The last green direction was V
// 2: The last green direction was H
byte last = 0;

proctype V(){
  do
    :: atomic{
      (semH==red && semH_clon==red && (last==0 || last==2));
      semV=green;
      semV=green;
      last=1;
    }
    printf("V: GREEN GREEN\n");
    semV=yellow;
    semV_clon=yellow;
    printf("V: YELLOW YELLOW\n");
    atomic{
      semV=red;
      semV_clon=red;
      printf("V: RED RED\n");
      printf("----------\n");
    }
  od
}

proctype H(){
  do
    :: atomic{
      (semV==red && semV_clon==red && (last==0 || last==1));
      semH=green;
      semH_clon=green;
      last=2;
    }
    printf("H: GREEN GREEN\n");
    semH=yellow;
    semH_clon=yellow;
    printf("H: YELLOW YELLOW\n");
    atomic{
      semH=red;
      semH_clon=red;
      printf("H: RED RED\n");
      printf("----------\n");
    }
  od
}

init {
  printf("V: RED RED\n");
  printf("H: RED RED\n");
  run V();
  run H();
}
