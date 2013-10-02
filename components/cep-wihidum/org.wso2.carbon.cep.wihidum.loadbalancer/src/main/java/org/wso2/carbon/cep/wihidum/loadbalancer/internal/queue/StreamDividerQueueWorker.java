package org.wso2.carbon.cep.wihidum.loadbalancer.internal.queue;

import org.apache.log4j.Logger;
import org.wso2.carbon.cep.wihidum.loadbalancer.conf.LoadBalancerConfiguration;
import org.wso2.carbon.cep.wihidum.loadbalancer.exception.EventPublishException;
import org.wso2.carbon.cep.wihidum.loadbalancer.nodemanager.Node;
import org.wso2.carbon.cep.wihidum.loadbalancer.utils.EventComposite;
import org.wso2.carbon.databridge.commons.Event;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class StreamDividerQueueWorker  implements Runnable {

    private List<Node> nodeList;
    private BlockingQueue<EventComposite> eventQueue;
    private static Logger logger = Logger.getLogger(QueueWorker.class);
    private LoadBalancerConfiguration loadBalancerConfiguration = LoadBalancerConfiguration.getInstance();

    public StreamDividerQueueWorker(List<Node> nodeList, BlockingQueue<EventComposite> eventQueue) {
        this.eventQueue = eventQueue;
        this.nodeList = nodeList;
    }


    @Override
    public void run() {
        EventComposite eventComposite = eventQueue.poll();
        List<Event> eventList = eventComposite.getEventList();
        for (Event evt : eventList){
            int nodeId = loadBalancerConfiguration.getAdjMatrix().get(evt.getStreamId());
            //TODO need to handle the case where given streamId is not added to the adjMatrix
            Node node = nodeList.get(nodeId);
            try {
                node.addEvent(evt);
            }catch (EventPublishException e) {
                logger.info("EventPublish Error" + e.getMessage());
            }
        }

    }
}
