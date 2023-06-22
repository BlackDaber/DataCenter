package ru.blackdaber.task.optimization;

import ru.blackdaber.task.DataCenter;
import ru.blackdaber.task.serverInfo.Server;

import java.util.List;

public class LoadBalancingOptimizationStrategy implements IOptimizationStrategy {
    @Override
    public void optimize(DataCenter dataCenter) {
        List<Server> servers = dataCenter.getServers();
        int numServers = servers.size();

        // Считаем среднюю нагрузку на сервер
        double totalLoad = totalLoad(servers);
        double averageLoad = totalLoad / numServers;

        // Распределяем нагрузку
        for (Server server : servers) {
            double serverLoad = server.getLoad();
            if (serverLoad <= averageLoad) {
                continue;
            }

            double excessLoad = serverLoad - averageLoad;
            server.setLoad(averageLoad);
            //Распределяем лишнюю нагрузку с сервера
            for (Server otherServer : servers) {
                double otherServerLoad = otherServer.getLoad();
                if (otherServerLoad >= averageLoad) {
                    continue;
                }

                double freeCapacity = averageLoad - otherServerLoad;
                if (excessLoad < freeCapacity) {
                    otherServer.setLoad(otherServerLoad + excessLoad);
                    break;
                } else {
                    otherServer.setLoad(averageLoad);
                    excessLoad -= freeCapacity;
                }
            }
        }
    }

    private double totalLoad(List<Server> servers) {
        double totalLoad = 0;
        for (Server server : servers) {
            totalLoad += server.getLoad();
        }

        return totalLoad;
    }
}
