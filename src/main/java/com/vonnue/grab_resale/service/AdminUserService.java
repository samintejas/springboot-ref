package com.vonnue.grab_resale.service;

import com.vonnue.grab_resale.web.dto.admin.InviteAdminRequest;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminResponse;

public interface AdminUserService {

    InviteAdminResponse inviteAdmin(InviteAdminRequest request);
}
