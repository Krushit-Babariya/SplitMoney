package com.krushit.service;

import com.krushit.entity.Group;
import com.krushit.entity.User;
import com.krushit.repository.GroupRepository;
import com.krushit.repository.UserRepository;
import com.krushit.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public Group addMemberToGroup(Long groupId, Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (groupOpt.isPresent() && userOpt.isPresent()) {
            Group group = groupOpt.get();
            User user = userOpt.get();
            group.getMembers().add(user);
            return groupRepository.save(group);
        }
        throw new RuntimeException("Group or User not found");
    }

    @Override
    public Group removeMemberFromGroup(Long groupId, Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (groupOpt.isPresent() && userOpt.isPresent()) {
            Group group = groupOpt.get();
            User user = userOpt.get();
            group.getMembers().remove(user);
            return groupRepository.save(group);
        }
        throw new RuntimeException("Group or User not found");
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
