package com.zhytnik.shop.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
public class SystemManager {

    private MemoryUsage memoryUsage;
    private OperatingSystemMXBean os;

    public SystemManager() {
        os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        memoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    }

    public System getSystemInfo() {
        final System s = new System();
        s.setCpuLoad(getCpuLoad());
        s.setJvmLoad(getJvmLoad());
        initialJvmMemoryInfo(s);
        initialSystemMemoryInfo(s);
        return s;
    }

    private Double getCpuLoad() {
        Double systemLoad = os.getSystemCpuLoad();
        systemLoad = (systemLoad != -1) ? round(systemLoad) : null;
        return systemLoad;
    }

    private Double getJvmLoad() {
        Double jvmLoad = os.getProcessCpuLoad();
        jvmLoad = (jvmLoad != -1) ? round(jvmLoad) : null;
        return jvmLoad;
    }

    private void initialJvmMemoryInfo(System system) {
        system.setUsingMemory(convertByteToMByte(memoryUsage.getUsed()));
        system.setInitialMemory(convertByteToMByte(memoryUsage.getInit()));
        system.setCommitedMemory(convertByteToMByte(memoryUsage.getCommitted()));
    }

    private void initialSystemMemoryInfo(System system) {
        system.setFreeMemory(getFreeMemorySize());
        system.setTotalMemory(getTotalMemorySize());
    }

    private Long getTotalMemorySize() {
        return convertByteToMByte(os.getTotalPhysicalMemorySize());
    }

    private Long getFreeMemorySize() {
        return convertByteToMByte(os.getFreePhysicalMemorySize());
    }

    private static double round(double value) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static long convertByteToMByte(long bytes) {
        return bytes / 1_000_000;
    }
}
