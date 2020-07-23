package com.hoon.electronic.repository;

import com.hoon.electronic.domain.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i " +
            "from Item i " +
            "join fetch i.category c " +
            "where c.id = :categoryId")
    Slice<Item> findItemList(Long categoryId, Pageable pageable);

    @Query("select i " +
            "from Item i " +
            "join fetch i.category c " +
            "where c.id = :categoryId and i.id < :cursorId")
    Slice<Item> findItemListByCursorId(Long categoryId, Long cursorId, Pageable pageable);

    @Query("select i " +
            "from Item i " +
            "where i.id in :itemIdList")
    List<Item> findItemListByItemIdList(List<Long> itemIdList);
}
