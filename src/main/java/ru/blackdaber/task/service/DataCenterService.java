package ru.blackdaber.task.service;

import ru.blackdaber.task.DataCenter;
import ru.blackdaber.task.serverInfo.ResourceRequest;
import ru.blackdaber.task.serverInfo.Server;
import ru.blackdaber.task.optimization.EnergyEfficencyOptimizationStrategy;
import ru.blackdaber.task.optimization.IOptimizationStrategy;
import ru.blackdaber.task.optimization.LoadBalancingOptimizationStrategy;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class DataCenterService {
    private final ScheduledExecutorService executorService;
    private final IOptimizationStrategy optimizationLoadStrategy = new LoadBalancingOptimizationStrategy();
    private final IOptimizationStrategy optimizationEnergyStrategy = new EnergyEfficencyOptimizationStrategy(this);
    private final DataCenter dataCenter;
    private static final long PERIOD = 30L;
    public DataCenterService(DataCenter dataCenter) {
        this.dataCenter = dataCenter;
        this.executorService = Executors.newScheduledThreadPool(1);
    }
    public void addServer(Server s) {
        dataCenter.servers.add(s);
    }
    public void removeServer(Server s) {
        dataCenter.servers.remove(s);
    }
    public int getTotalEnergyConsumption() {
        int total = 0;
        for (Server server : dataCenter.servers) {
            total += server.getEnergyConsumption();
        }
        return total;
    }
    public void allocateResources(ResourceRequest request){
        for (Server server : dataCenter.servers) {
            double requestLoad = request.getLoad();
            double serverMaxLoad = server.getMaxLoad();
            double serverLoad = server.getLoad();

            if(serverLoad >= serverMaxLoad) {
                continue;
            }

            double counter = Math.min(requestLoad, serverMaxLoad - serverLoad);
            server.setLoad(serverLoad + counter);
            request.setLoad(requestLoad - counter);
            if (request.getLoad() == 0) {
                return;
            }
        }
    }
    public void releaseResources(ResourceRequest request) {

        //Высвобождаем ресурсы начиная с последнего непустого (по нагрузке) сервера.
        for(int i = dataCenter.servers.size() - 1; i >= 0; i--) {
            Server server = dataCenter.servers.get(i);
            double requestLoad = request.getLoad();
            double serverLoad = server.getLoad();
            if(serverLoad == 0) {
                continue;
            }

            double counter = Math.min(requestLoad, serverLoad);
            server.setLoad(serverLoad - counter);
            request.setLoad(requestLoad - counter);
            if (request.getLoad() == 0) {
                return;
            }
        }
    }
    //Реализация метода, который будет вызывать оптимизацию дата центра раз в определённое время
    public void timerLoadOptimization() {
        long LOAD_DELAY = 5L;
        executorService.scheduleAtFixedRate(() -> {
            try {
                optimizationLoadStrategy.optimize(dataCenter);
                logger(dataCenter.servers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, LOAD_DELAY, PERIOD, TimeUnit.SECONDS);
    }
    public void timerEnergyOptimization() {
        long ENERGY_DELAY = 15L;
        executorService.scheduleAtFixedRate(() -> {
            try {
                optimizationEnergyStrategy.optimize(dataCenter);
                logger(dataCenter.servers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ENERGY_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    private void logger(List<Server> servers) {
        servers.forEach(server -> System.out.println("load of server - " + server.getLoad()));
        System.out.println("Servers energy consumption - " + getTotalEnergyConsumption());
    }
}
