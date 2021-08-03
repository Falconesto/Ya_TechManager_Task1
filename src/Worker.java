import java.util.TreeSet;

public class Worker {
    private TreeSet<Task> taskset = new TreeSet<>((t1, t2) -> t1.getAssigned_ts().isBefore(t2.getAssigned_ts()) ? -1 : 1);

    public Task[] getTaskArray() {
        Task[] taskArray = new Task[taskset.size()];
        return taskset.toArray(taskArray);
    }

    void addTask(Task t){
        taskset.add(t);
    }
}