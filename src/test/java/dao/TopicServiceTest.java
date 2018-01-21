package dao;

import java.util.List;

import com.cosyit.lmbbs.entity.Topic;
import com.cosyit.lmbbs.factory.ServiceFactory;

public class TopicServiceTest {
	public static void main(String[] args) {
/*		//创建一个赤裸裸的对象，没有数据
		Topic topic=new Topic();
		topic.setId("0001");
		topic.setTitle("testTitle");
		topic.setFloor(1);
		topic.setContent("八嘎反倒是");
		topic.setType("xxx");
		//包装数据。先把数据准备好
		TopicService ts=ServiceFactory.getTopicService();
		ts.addTopic(topic, "98d9bb6d-6633-4bb0-9392-1b1b15b607c4", "5e1de632-68f9-4756-96f5-f76df3f3aa73");*/
		
		List<Topic> topics = ServiceFactory.getTopicService().getAllTopicis("5e1de632-68f9-4756-96f5-f76df3f3aa73");
		System.out.println(topics.size());
		
	}
}
