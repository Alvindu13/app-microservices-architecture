package com.rattrapage.microserviceapi.utils;

import com.rattrapage.microserviceapi.persist.models.Files;
import org.springframework.content.commons.repository.ContentStore;

public interface FileContentStore extends ContentStore<Files, String> {
}
