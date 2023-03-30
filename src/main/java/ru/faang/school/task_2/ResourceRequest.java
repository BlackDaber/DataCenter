package ru.faang.school.task_2;

public class ResourceRequest {
    private double load;
    public ResourceRequest(double load) {
        validate(load);
        this.load = load;
    }
    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        validate(load);
        this.load = load;
    }
    public void validate(double load) {
        if (load < 0) {
            throw new IllegalArgumentException("Нагрузка должна быть >= 0");
        }
    }
}
