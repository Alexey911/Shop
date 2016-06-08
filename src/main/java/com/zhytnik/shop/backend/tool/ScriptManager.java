package com.zhytnik.shop.backend.tool;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.zhytnik.shop.App.*;
import static com.zhytnik.shop.util.FileLoader.loadDirectoryFiles;
import static java.util.Collections.sort;

/**
 * @author Alexey Zhytnik
 * @since 08.06.2016
 */
class ScriptManager {

    private boolean continueOnError;
    private boolean ignoreFailedDrops;
    private boolean dropCreate;
    private String provider;

    private DataSource dataSource;

    private boolean executed;

    public void update() {
        if (dropCreate && !executed) {
            executeScripts(getSortedScripts(DDL_SCRIPTS_PATH + provider + DDL_DROP_SCRIPTS_PATH));
            executeScripts(getSortedScripts(DDL_SCRIPTS_PATH + provider));
            executeScripts(getSortedScripts(DML_SCRIPTS_PATH + provider));
            executed = true;
        }
    }

    void executeScripts(Resource[] scripts) {
        final ResourceDatabasePopulator p = new ResourceDatabasePopulator(scripts);
        p.setIgnoreFailedDrops(ignoreFailedDrops);
        p.setContinueOnError(continueOnError);
        p.setSeparator("/");
        p.execute(dataSource);
    }

    private Resource[] getSortedScripts(String path) {
        final List<Resource> scripts = getScripts(path);
        sort(scripts, new Comparator<Resource>() {
            @Override
            public int compare(Resource a, Resource b) {
                return a.getFilename().compareTo(b.getFilename());
            }
        });
        return scripts.toArray(new Resource[scripts.size()]);
    }

    private List<Resource> getScripts(String path) {
        final List<Resource> scripts = new ArrayList<>();
        for (File file : loadDirectoryFiles(path)) {
            if (!file.isDirectory()) {
                scripts.add(new FileSystemResource(file.getPath()));
            }
        }
        return scripts;
    }

    public void setDropCreate(boolean dropCreate) {
        this.dropCreate = dropCreate;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public void setIgnoreFailedDrops(boolean ignoreFailedDrops) {
        this.ignoreFailedDrops = ignoreFailedDrops;
    }

    @Required
    public void setProvider(String provider) {
        this.provider = provider.toLowerCase();
    }
}
