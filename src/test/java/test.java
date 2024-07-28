import cn.hutool.core.date.DateUtil;
import io.lettuce.core.ScriptOutputType;
import org.apache.logging.log4j.core.config.Scheduled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.lang.reflect.Array;
import java.util.*;

public class test {
    @Test
    public void test11(){
//        LinkedHashMap<Integer, Integer> integerIntegerLinkedHashMap = new LinkedHashMap<>(2,0.75f,true);
//        HashMap<Integer, Integer> integerIntegerLinkedHashMap = new HashMap<>();
        LRUC lruc = new LRUC(2);
        lruc.put(1,2);
        lruc.put(2,4);
        lruc.put(3,6);
        lruc.put(4,8);
//        System.out.println(lruc.);
//        integerIntegerLinkedHashMap.get()

        for(Map.Entry<Integer,Integer> entry : lruc.entrySet()){
            System.out.println(entry);
        }

//        Timer timer = new Timer();
//        new ThreadPoolTaskExecutor()
//        new ThreadPoolTaskScheduler()
//        System.out.println(new Date());
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("运行中，运行时间为:" + DateUtil.now());
//            }
//        },1000,5000L);

    }
}
class LRUC extends LinkedHashMap<Integer,Integer>{

    private int capacity;

    public LRUC() {
    }

    public LRUC(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
