package JavaCore.Lesson5;

/**
 * JavaCore.Lesson5
 * Created by Михаил Силантьев on 18.10.2017.
 */
public class MyThread extends Thread{
    private volatile float[] arr;
    private volatile int countArr=0;

    @Override
    public void run(){
        //System.out.println(Thread.currentThread().getName());
        metod(countArr);
    }

    MyThread(String name,float[] arr,int countArr){
        super(name);
        this.arr=arr;
        this.countArr=countArr;
    }

    public synchronized float[] getArr() {
        return arr;
    }

    public synchronized long metod(int h){
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
        }
        return System.currentTimeMillis()-start;
    }
}
