package springboot_system.jiaoyu_system;


import com.jiaoyu.dao.CandidateMapper;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.entity.ManageRoom;
import com.jiaoyu.entity.PersonReceive;
import com.jiaoyu.service.ICandidateService;
import com.jiaoyu.service.IPersonReceiveService;
import com.sun.org.apache.xpath.internal.axes.ReverseAxesWalker;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import org.apache.shiro.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JiaoyuSystemApplicationTests {
	@Autowired
	private ICandidateService candidateService;
	@Autowired
	private IPersonReceiveService personReceiveService;

  @Test
	public void addCandidate(){
       /**  <th width="40">ID</th>
		<th width="150">考场名称</th>
		<th width="90">地点</th>
		<th width="150">座位数量</th>
		<th width="150">考场批次</th>
		<th width="130">座位剩余数</th>
		<th width="130">所在教室</th>
		<th width="100">操作及状态</th>*/
	  for (int i = 1; i <=10; i++) {
		  ManageRoom manageRoom=new ManageRoom();
		  manageRoom.setClassroomId(null);


	  }
	}
	@Test
	public void seatNum (){
		String realSeatNum=null;
		List<Candidate> list = candidateService.getList();
		for (Candidate candidate : list) {
			if(candidate.getCandidateOccasion().equals("未分配")){
				String seatNum = candidate.getSeatNum();
				 realSeatNum = seatNum.substring(seatNum.lastIndexOf("配")+1);


			}

		}
		System.out.println(realSeatNum.length());
	}
	@Test
	public void firstSeatNum(){
		Candidate candidate = candidateService.getById(1);
		String seatNum = candidate.getSeatNum();
		 seatNum=new StringBuffer(seatNum).reverse().toString();
		String seatNum1 = seatNum.substring(seatNum.lastIndexOf("号")+1);
		System.out.println(seatNum1);

	}
	@Test
	public void updatePersonReveive(){
		//修改person_reveive中成绩和录取情况
		PersonReceive receive=new PersonReceive();
		for (int i = 1; i <= 50; i++) {
             receive.setPersonId(i);
             receive.setKaoshengScore(60.0+i);
             receive.setLuquOccasion("拟录取");
             personReceiveService.udpate(receive);
		}



	}
}
