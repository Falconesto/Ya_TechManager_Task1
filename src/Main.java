import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Worker> workers = new HashMap<>();

        String inputFileName = "D:\\labs\\ya_testTask\\src\\файл_1.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\t");
                if(!workers.containsKey(words[0])){
                    Task newTask = new Task(Double.parseDouble(words[1]), Double.parseDouble(words[2]),
                            LocalDateTime.parse(words[3].replace(" ", "T")),
                            LocalDateTime.parse(words[4].replace(" ", "T")));
                    Worker newWorker = new Worker();
                    newWorker.addTask(newTask);
                    workers.put(words[0], newWorker);
                }else{
                    Task newTask = new Task(Double.parseDouble(words[1]), Double.parseDouble(words[2]),
                            LocalDateTime.parse(words[3].replace(" ", "T")),
                            LocalDateTime.parse(words[4].replace(" ", "T")));
                    workers.get(words[0]).addTask(newTask);
                }
            }
            double sumOfWorkerTimePerTask = 0;
            for (Worker worker : workers.values()){
                Task[] tasks = worker.getTaskArray();
                LocalDateTime start = tasks[0].getAssigned_ts();
                LocalDateTime prevEnd = tasks[0].getClosed_ts();
                double microtasksInPeriod = tasks[0].getMicrotasks();
                double timePerTasks = 0;
                int numOfPeriods = 1;
                for (int i=1; i<tasks.length; i++) {
                    if (tasks[i].getAssigned_ts().isBefore(prevEnd)){
                        microtasksInPeriod+=tasks[i].getMicrotasks();
                        prevEnd = prevEnd.isBefore(tasks[i].getClosed_ts()) ? tasks[i].getClosed_ts() : prevEnd;
                    }else{
                        timePerTasks= timePerTasks==0 ? Duration.between(start, prevEnd).getSeconds()/microtasksInPeriod :
                                (timePerTasks + Duration.between(start, prevEnd).getSeconds()/microtasksInPeriod);
                        start = tasks[i].getAssigned_ts();
                        prevEnd = tasks[i].getClosed_ts();
                        microtasksInPeriod = tasks[i].getMicrotasks();
                        numOfPeriods++;
                    }
                }
                timePerTasks= timePerTasks==0 ? Duration.between(start, prevEnd).getSeconds()/microtasksInPeriod :
                        (timePerTasks + Duration.between(start, prevEnd).getSeconds()/microtasksInPeriod);
                numOfPeriods++;
                sumOfWorkerTimePerTask+=timePerTasks/numOfPeriods;
            }
            double overallTimePerTask = sumOfWorkerTimePerTask/workers.size();
            System.out.printf("The average time per microtask is %.4f %n", overallTimePerTask);
            double moneyPerMicrotask = overallTimePerTask/30;
            System.out.printf("So the amount of money employee should get per microtask is about %.4f*N of rubles", moneyPerMicrotask);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}