package com.springbatch.batch;

import com.springbatch.entity.Advertisement;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcess implements ItemProcessor<Advertisement,Advertisement> {
    @Override
    public Advertisement process(Advertisement item) throws Exception {
        item.setImpressions(item.getImpressions()+1);
        return item;
    }
}