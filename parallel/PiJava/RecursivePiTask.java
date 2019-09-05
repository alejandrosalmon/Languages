import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class RecursivePiTask extends RecursiveTask<Double> {

    double anchoIntervalo;
    long desde;
    long hasta;

    public RecursivePiTask(double anchoIntervalo, long desde, long hasta) {
        this.anchoIntervalo = anchoIntervalo;
        this.desde = desde;
        this.hasta = hasta;
    }

    public double calcular() {
        double valorParcial = 0;
        double x;
        for (long i = desde; i < hasta; i++) {
            x = (i + 0.5) * anchoIntervalo;
            valorParcial += 4 / (1 + (x * x));
        }
        return valorParcial;
    }

    @Override
    public Double compute() {
        if (hasta - desde < 10000) {
            return calcular();
        } else {
            double result = 0;

            List<RecursiveTask<Double>> forks = new ArrayList<>();
            forks.addAll(repartirTrabajo());

            for (RecursiveTask<Double> fork : forks)
                fork.fork();

            for (RecursiveTask<Double> fork : forks)
                result += fork.join();

            return result;
        }
    }

    private Collection<? extends RecursivePiTask> repartirTrabajo() {
        List<RecursivePiTask> forks = new ArrayList<>();

        RecursivePiTask task1 = new RecursivePiTask(this.anchoIntervalo, desde, desde + (hasta - desde) / 2);
        RecursivePiTask task2 = new RecursivePiTask(this.anchoIntervalo, desde + (hasta - desde) / 2, desde + (hasta - desde));

        forks.add(task1);
        forks.add(task2);

        return (forks);
    }
}