package com.example.springbootsampleec.services.impl;

import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.services.ItemService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// gradle で追加
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

@Service
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;

	@Autowired
	private Environment environment; // 環境変数を使えるように。

	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Item> findById(long id) {
		return itemRepository.findById(id);
	}

	/**
	 * 追記　商品名で検索する
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Item> findByNameContaining(String keyword) {
		return itemRepository.findByNameContaining(keyword);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateItem(long id, String name, int price, int stock, String description) {
		Item item = findById(id).orElseThrow();
		item.setName(name);
		item.setPrice(price);
		item.setStock(stock);
		item.setDescription(description);
		itemRepository.saveAndFlush(item);
	}

	@Transactional
	@Override
	public void delete(long id) {
		Item item = findById(id).orElseThrow();
		itemRepository.delete(item);
	}

	@Transactional
	@Override
	public void register(String name, int price, int stock, String description, MultipartFile image) {
		if (image.getOriginalFilename().isEmpty()) {
			throw new RuntimeException("ファイルが設定されていません");
		}
		// 拡張子取得
		String extension = FilenameUtils.getExtension(image.getOriginalFilename());
		// ランダムなファイル名を設定
		String randomFileName = RandomStringUtils.randomAlphanumeric(20) + "." + extension;
		uploadImage(image, randomFileName);
		// Item エンティティの生成
		Item item = new Item(null, name, price, stock, description, randomFileName, null, null);

		// Item を保存
		itemRepository.saveAndFlush(item);
	}

	private void uploadImage(MultipartFile multipartFile, String fileName) {
		// 保存先のパスを作成
		Path filePath = Paths.get(environment.getProperty("sample.images.imagedir") + fileName);
		try {
			// ファイルをバイト列に変換して書き込み
			byte[] bytes = multipartFile.getBytes();
			OutputStream stream = Files.newOutputStream(filePath);
			stream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}