public class RunnableTest implements Runnable {
    private final int x;
    private final int y;

    private final Circle firstCircle, secondCircle, thirdCircle, fourthCircle;
    private final DrawingPanel dp;

    public RunnableTest(final DrawingPanel dp, final int x, final int y) {
        this.dp = dp;
        this.x = x;
        this.y = y;

        firstCircle = new Circle(x, y, 20);
        secondCircle = new Circle(x, y, 30);
        thirdCircle = new Circle(x, y, 40);
        fourthCircle = new Circle(x, y, 50);
    }

    int status = 0;

    public void run() {
        while (true) {
            switch (status) {
                case 0:
                    dp.circles.add(firstCircle);
                    status = 1;
                    break;
                case 1:
                    dp.circles.add(secondCircle);
                    status = 2;
                    break;
                case 2:
                    dp.circles.add(thirdCircle);
                    status = 3;
                    break;
                case 3:
                    dp.circles.add(fourthCircle);
                    status = 4;
                    break;
                case 4:
                    dp.circles.remove(fourthCircle);
                    status = 5;
                    break;
                case 5:
                    dp.circles.remove(thirdCircle);
                    status = 6;
                    break;
                case 6:
                    dp.circles.remove(secondCircle);
                    status = 1;
                    break;
            }
            dp.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}