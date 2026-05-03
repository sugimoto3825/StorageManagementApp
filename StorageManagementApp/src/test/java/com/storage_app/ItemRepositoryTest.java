package com.storage_app;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.storage_app.entity.Item;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ItemRepositoryTest {
	
    @Autowired
    ItemRepository repos;

    // ==================== CREATE & READ ====================
    @Test
    @DisplayName("アイテム取得：正常系")
    void testFindById() {
    	Optional<Item> item = repos.findById(1);
    	assertThat("アイテムが存在しない", item.isPresent(), is(true));
    	assertThat("アイテム名が一致しない", item.get().getName(), is("全データ"));
    	assertThat("親アイテムIDが一致しない", item.get().getParentItemId(), is(0));
    	assertThat("子アイテム番号が一致しない",item.get().getChildNo(), is(1));
    }
	
}
