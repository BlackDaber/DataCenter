# DataCenter

Представьте, что Вы работаете в крупнейшей компании, с большим количеством дата-центров в разных уголках планеты. Вам нужно создать систему, которая позволит определить оптимальное распределение ресурсов и обеспечить эффективное использование серверов в дата-центре.

Выполнение этой задачи помогло лучше понять концепции ООП, реализовать паттерн Стратегия и в целом попрактиковать навыки проектирования и реализации систем с использованием различных компонентов.

Требования:

1. Создать класс Server, который будет содержать следующие характеристики:
      double load - отражает текущую нагрузку на сервер
      double maxLoad - отражает максимальную нагрузку на сервер
      double energyConsumption - отражает текущие энергозатраты сервера

2. Создать класс DataCenter, который будет хранить список серверов.

3. Создать класс ResourceRequest с полем double load - будет использоваться, когда к нашему дата-центру приходит запрос на выделения ресурса

4. Создать класс DataCenterService, в котором будут:
      методы для добавления и удаления сервера,
      метод для получения информации о потреблении электроэнергии всеми серверами (getTotalEnergyConsumption()),
      методы для выделения и высвобождения ресурсов на запрос (allocateResources(ResourceRequest request), releaseResources(ResourceRequest request))

5. Создать интерфейс OptimizationStrategy с методом void optimize(DataCenter dataCenter), использовать этот метод в DataCenterService для оптимизации нагрузки на дата-центр. Например, создать метод, который будет проводить оптимизацию нагрузки каждые полчаса.

6. Создать реализации OptimizationStrategy, например LoadBalancingOptimizationStrategy или EnergyEfficencyOptimizationStrategy, где вы напишите алгоритм по распределению нагрузки по серверам или алгоритм оптимизации энергопотребления в дата-центре
