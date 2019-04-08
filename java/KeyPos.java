public class KeyPos {
   private int x;
   private int y;
   private char c;
   public KeyPos(char c, int x, int y){
      this.x = x;
      this.y = y;
      this.c = c;
   }
   
   public char getKey(){ return c; }
   public int getX(){ return x; }
   public int getY(){ return y; }
   public int getDistance(KeyPos k){
      return (int)(Math.abs(k.x - x) + Math.abs(k.y - y));
   }
}