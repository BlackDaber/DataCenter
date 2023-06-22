package ru.blackdaber.task.serverInfo;

public class Server {
    private double load;
    private final double maxLoad;
    private final double energyConsumption;

    public Server(double maxLoad, double energyConsumption) {
        validate(maxLoad, energyConsumption);
        this.load = 0;
        this.maxLoad = maxLoad;
        this.energyConsumption = energyConsumption;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        if (load < 0) {
            throw new IllegalArgumentException("Нагрузка должна быть >= 0");
        }
        if (load > maxLoad) {
            throw new IllegalArgumentException("Нагрузка должна быть меньше максимальной");
        }
        this.load = load;
    }

    public double getMaxLoad() {
        return maxLoad;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void validate(double maxLoad, double energyConsumption) {
        if (maxLoad <= 0) {
            throw new IllegalArgumentException("Максимальная нагрузка должна быть больше 0");
        }
        if (energyConsumption <= 0) {
            throw new IllegalArgumentException("Энергозатраты должны быть больше 0");
        }
    }

    @Override
    public String toString() {
        return "Create new server " +
                "\n max load - " + maxLoad +
                "\n energy consumption of server - " + energyConsumption;
    }
}
