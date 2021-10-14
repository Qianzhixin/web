package team.ifp.cbirc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GuoXinyuan
 * @date 2021/10/13
 * 锁工具类
 */

public class LockUtil {

    /**
     * 锁
     */
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMap = new ConcurrentHashMap<>();

    /**
     * 创建锁并锁定
     * @param key
     */
    public void lock(String key,String t) {
        //尝试创建锁 并更新锁定数量
        synchronized (key.intern()) {
            lockMap.computeIfPresent(key,(k,v)->{
                countMap.put(key,countMap.get(key)+1);
                return v;
            });
            lockMap.computeIfAbsent(key,k -> {
                ReentrantLock reentrantLock = new ReentrantLock();
                countMap.put(key,1);
                return reentrantLock;
            });
        }
        //尝试获取锁
        lockMap.get(key).lock();
    }

    /**
     * 解锁
     * @param key
     */
    public synchronized void unlock(String key,String t) {

        ReentrantLock lock = lockMap.computeIfPresent(key,(k,v)->{
            countMap.put(key,countMap.get(key)-1);
            return null;
        });
        if(countMap.get(key) == 0) {
            countMap.remove(key);
            lockMap.remove(key);
        }
        assert lock != null;
        lock.unlock();
    }

    /**
     * 获取当前 key 对应锁等待个数
     * @param key
     * @return
     */
    public int getWaitCount(String key) {
        ReentrantLock lock = lockMap.get(key);
        if(lock == null) return 0;
        return lock.getQueueLength();
    }

    public static int a = 0;

    public static void main(String[] args) {
        LockUtil lockUtil = new LockUtil();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{

                lockUtil.lock("1",Thread.currentThread().getName());
                a++;

                System.out.println(Thread.currentThread().getName() + "运行中");
                lockUtil.unlock("1",Thread.currentThread().getName());
            },"t"+i).start();
        }
    }

}
