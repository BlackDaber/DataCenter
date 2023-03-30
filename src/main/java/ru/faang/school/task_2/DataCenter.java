package ru.faang.school.task_2;

import ru.faang.school.task_2.service.DataCenterService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataCenter {
    public final List<Server> servers = new ArrayList<>();
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final DataCenterService service = new DataCenterService(this);

    public static void main(String[] args) {
        DataCenter dataCenter = new DataCenter();
        dataCenter.test();
    }
    public void test() {
        int size = 8;
        addServers(size);
        servers.forEach(server -> {
            server.setLoad(random.nextDouble(150));
            System.out.println("Start load of server - " + server.getLoad());
        });
        service.timerLoadOptimization();
        service.timerEnergyOptimization();
    }
    private void addServers(int size) {
        double maxLoad = random.nextDouble(500);
        double energyConsumption = random.nextDouble(1000);
        for(int i = 1; i <= size; i++) {
            service.addServer(createServer(maxLoad, energyConsumption));
        }
    }
    private Server createServer(double maxLoad, double energyConsumption) {
        Server server = new Server(maxLoad, energyConsumption);
        System.out.println(
                "Create new server " +
                        "\n max load - " + maxLoad +
                        "\n energy consumption of server - " + energyConsumption
        );
        return server;
    }

    public List<Server> getServers() {
        return servers;
    }
}
