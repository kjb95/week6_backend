package com.springbatch.batch.process;

import com.springbatch.entity.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class CustomItemProcess implements ItemProcessor<Advertisement, Advertisement> {
    private final String date;

    @Override
    public Advertisement process(Advertisement item) throws Exception {
        item.setImpressions(item.getImpressions() + 1);
        if (date != null && !date.equals(item.getPeriod())) {
            return null;
        }

        return item;
    }
}