package ru.blackdaber.task.optimization;

import ru.blackdaber.task.serverInfo.ResourceRequest;
import ru.blackdaber.task.DataCenter;
import ru.blackdaber.task.service.DataCenterService;


public class EnergyEfficencyOptimizationStrategy implements IOptimizationStrategy {
    DataCenterService dataCenterService;

    public EnergyEfficencyOptimizationStrategy(DataCenterService dataCenterService) {
        this.dataCenterService = dataCenterService;
    }

    @Override
    public void optimize(DataCenter dataCenter) {
        double allServersLoad = 0;
        int size = dataCenter.servers.size();
        for (int i = 0; i < size; i++) {
            double load = dataCenter.servers.get(i).getLoad();
            if (load == 0) {
                continue;
            }
            allServersLoad += load;
        }
        if (allServersLoad == 0) {
            return;
        }
        ResourceRequest releaseRequest = new ResourceRequest(allServersLoad);
        ResourceRequest allocateRequest = new ResourceRequest(allServersLoad);

        dataCenterService.releaseResources(releaseRequest);
        dataCenterService.allocateResources(allocateRequest);
    }
}
