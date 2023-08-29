package com.vaistra.services.impl;

import com.vaistra.dto.CategoryDTO;
import com.vaistra.dto.TagDTO;
import com.vaistra.entities.Category;
import com.vaistra.entities.Tag;
import com.vaistra.exception.DuplicateEntryException;
import com.vaistra.exception.ResourceNotFoundException;
import com.vaistra.repositories.TagRepository;
import com.vaistra.services.TagService;
import com.vaistra.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public TagDTO addTag(TagDTO tagDTO) {
        Tag t = tagRepository.findByTagName(tagDTO.getTagName());

        if (t != null)
            throw new DuplicateEntryException("Tag name: " + t.getTagName() + " already exist..!");

        tagDTO.setTagName(tagDTO.getTagName().toUpperCase());
        tagDTO.setTagDescription(tagDTO.getTagDescription());

        return AppUtils.tagToDto(tagRepository.save(AppUtils.dtoToTag(tagDTO)));
    }

    @Override
    public TagDTO getTagById(int id) {

        Tag t = tagRepository.findByIdNotTrashed(id);
        if (t == null)
            throw new ResourceNotFoundException("Tag with id '" + id + "' Not Found!");
        else
            return AppUtils.tagToDto(t);
    }

    @Override
    public TagDTO getTagByIdTrashed(int id) {
        Tag t = tagRepository.findByIdTrashed(id);
        if (t == null)
            throw new ResourceNotFoundException("Tag with id '" + id + "' Not Found!");
        else
            return AppUtils.tagToDto(t);
    }

    @Override
    public TagDTO getTagByIdInActive(int id) {

        Tag t = tagRepository.findByIdInActive(id);
        if (t == null)
            throw new ResourceNotFoundException("Category with id '" + id + "' Not Found!");
        else
            return AppUtils.tagToDto(t);
    }

    @Override
    public List<TagDTO> getAllTags() {

        List<Tag> tags = tagRepository.findAll();

        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return AppUtils.tagsToDtos(tags);
        }
    }

    public List<TagDTO> getAllTags(int pageNo, int pageSize, String sortBy, String sortOrder) {
        List<TagDTO> TDto = new ArrayList<>();

        Sort s = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNo, pageSize, s);

        Page<Tag> tags = tagRepository.findAll(p);
        List<Tag> content = tags.getContent();

        if (content.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {

            for (Tag tag : content) {
                TagDTO t = AppUtils.tagToDto(tag);
                TDto.add(t);
            }
            return TDto;
        }
    }

    @Override
    public List<TagDTO> getAllTagsSortBy(String field) {
        return null;
    }

    @Override
    public List<TagDTO> getAllTrashedTags() {
        List<TagDTO> TDto = new ArrayList<>();

        List<Tag> tags = tagRepository.findAllTrashed();

        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return AppUtils.tagsToDtos(tags);
        }
    }

    @Override
    public List<TagDTO> getAllInActiveTags() {

        List<Tag> tags = tagRepository.findAllInActive();

        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return AppUtils.tagsToDtos(tags);
        }
    }

    @Override
    public TagDTO updateTagById(int id, TagDTO tagDTO) {

//        t = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag with Id '" + id + "' not found!"));

        Tag t = tagRepository.findByIdNotTrashed(id);
        if (t == null) {
            throw new ResourceNotFoundException("Tag with Id '" + id + "' not found!");
        } else {
            if (tagDTO.getTagName() != null) {
                if (tagRepository.findByTagName(tagDTO.getTagName()) != null)
                    throw new DuplicateEntryException("Tag name: " + tagDTO.getTagName() + " already exist..!");
                else
                    t.setTagName(tagDTO.getTagName().toUpperCase());
            }
            t.setTagDescription(tagDTO.getTagDescription());
            t.setActive(tagDTO.isActive());
            t.setPosts(AppUtils.dtosToPosts(tagDTO.getPosts()));
            tagRepository.save(t);
        }
        return AppUtils.tagToDto(t);
    }

    @Override
    public TagDTO updateStatusById(int id, boolean status) {
        return null;
    }

    @Override
    public int deleteTagById(int id) {
        Tag t = tagRepository.findByIdNotTrashed(id);
        if (t != null) {
            tagRepository.delete(t);
            return 1;
        } else
            throw new ResourceNotFoundException("Tag with Id '" + id + "' not found!");
    }

    @Override
    public int trashTagById(int id) {
        Tag t = tagRepository.findByIdNotTrashed(id);
        if (t != null) {
            t.setActive(Boolean.FALSE);
            t.setDeleted(Boolean.TRUE);
            t.setDeletedAt(new Date());
            tagRepository.save(t);
            return 1;
        } else
            throw new ResourceNotFoundException("Tag with Id '" + id + "' not found!");
    }

    @Override
    public int restoreTagById(int id) {
        Tag t = tagRepository.findByIdTrashed(id);

        if (t != null) {
            t.setActive(Boolean.TRUE);
            t.setDeleted(Boolean.FALSE);
            t.setDeletedAt(null);
            tagRepository.save(t);
            return 1;
        } else
            throw new ResourceNotFoundException("Tag with Id '" + id + "' not found!");
    }
}
