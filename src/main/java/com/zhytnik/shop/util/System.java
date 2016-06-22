package com.zhytnik.shop.util;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
public class System {

    private Double cpuLoad;
    private Double jvmLoad;

    private Long usingMemory;
    private Long initialMemory;
    private Long commitedMemory;

    private Long totalMemory;
    private Long freeMemory;

    public System() {
    }

    public Double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(Double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public Double getJvmLoad() {
        return jvmLoad;
    }

    public void setJvmLoad(Double jvmLoad) {
        this.jvmLoad = jvmLoad;
    }

    public Long getUsingMemory() {
        return usingMemory;
    }

    public void setUsingMemory(Long usingMemory) {
        this.usingMemory = usingMemory;
    }

    public Long getInitialMemory() {
        return initialMemory;
    }

    public void setInitialMemory(Long initialMemory) {
        this.initialMemory = initialMemory;
    }

    public Long getCommitedMemory() {
        return commitedMemory;
    }

    public void setCommitedMemory(Long commitedMemory) {
        this.commitedMemory = commitedMemory;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }
}
