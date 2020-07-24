package com.hoon.electronic.repository;

import com.hoon.electronic.domain.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "WHERE c.id = :categoryId")
    Slice<Item> findItemList(Long categoryId, Pageable pageable);

    @Query("SELECT i " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "WHERE c.id = :categoryId AND i.id < :cursorId")
    Slice<Item> findItemListByCursorId(Long categoryId, Long cursorId, Pageable pageable);

    @Query("SELECT i " +
            "FROM Item i " +
            "WHERE i.id IN :itemIdList")
    List<Item> findItemListByItemIdList(List<Long> itemIdList);
}
