public class TestProgram4 extends AbstractTestProgram4 implements ITestProgram4 {

  static {
    TestProgram4.class.getName();
  }

  public void method1() {
    this;
    super;
    1;
    a;
    TestProgram4.class;
    void.class;

    this(1);
    super(1);
    super.test(1);
    test(1);
    <TestProgram4>this(1);
    <TestProgram4>super(1);
    <TestProgram4>super.test(1);
    <TestProgram4>test(1);

    TestProgram4.a;
    TestProgram4.this;
    TestProgram4.new TestProgram4(1);
    TestProgram4.new <TestProgram4>TestProgram4(1);
    TestProgram4.super(1);
    TestProgram4.<TestProgram4>test(1);

    1[1];
    test(1, 2, 3);
    new TestProgram4(1, 2, 3) {
      private int i;
      private void f() {
      }
    };
    (TestProgram4) 1;

    1++;
    1--;
    +1;
    -1;
    ++1;
    --1;
    ~1;
    !1;

    1 * 1;
    1 / 1;
    1 % 1;
    1 + 1;
    1 - 1;
    1 << 1;
    1 >>> 1;
    1 >> 1;
    1 <= 1;
    1 >= 1;
    1 < 1;
    1 > 1;
    1 instanceof TestProgram4;
    1 == 1;
    1 != 1;
    1 & 1;
    1 ^ 1;
    1 | 1;
    1 && 1;
    1 || 1;

    true ? 1 : 1;

    1 = 1;
    1 += 1;
    1 -= 1;
    1 *= 1;
    1 /= 1;
    1 &= 1;
    1 |= 1;
    1 ^= 1;
    1 >>= 1;
    1 >>>= 1;
    1 <<= 1;
    1 %= 1;

  }
}

interface ITestProgram4 {
  public void method1();
  public String method2();
}

abstract class AbstractTestProgram4 implements ITestProgram4 {
  public abstract String method2();
}