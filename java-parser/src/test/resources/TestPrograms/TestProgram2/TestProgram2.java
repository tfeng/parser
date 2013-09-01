package TestPrograms;

public class TestProgram2 {

  private Object o = new Object() {
    {
      System.out.println(this);
    }

    public void method() {
      // Method
    }
  };

  public static void main(String[] args) {
    int[] a[] = new int[][] {
      {0,1,2}, {3,4,5}
    };
  }
}
