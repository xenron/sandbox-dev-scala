import java.util.Arrays;
import java.util.List;

public class TaskDriver {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list1 = Arrays.asList(10, 11, 12, 13);
        List<Integer> list2 = Arrays.asList(14, 15, 16);

        ListSumTask task1 = new ListSumTask(list1);
        Thread t1 = new Thread(task1);
        ListSumTask task2 = new ListSumTask(list2);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int total = task1.getAcc() + task2.getAcc();
        System.out.println("Total sum = " + total);
    }
}

