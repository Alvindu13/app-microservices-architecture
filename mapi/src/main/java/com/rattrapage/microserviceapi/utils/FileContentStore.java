package com.rattrapage.microserviceapi.utils;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import org.springframework.content.commons.repository.ContentStore;

public interface FileContentStore extends ContentStore<FileApp, String> {
}
