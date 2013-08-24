/**
 * Copyright 2013 Huining (Thomas) Feng (tfeng@berkeley.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bacoder.parser.java.api;

public class MemberClassBodyDeclaration extends ClassBodyDeclaration {

  private MemberDeclaration memberDeclaration;

  public MemberDeclaration getMemberDeclaration() {
    return memberDeclaration;
  }

  public void setMemberDeclaration(MemberDeclaration memberDeclaration) {
    this.memberDeclaration = memberDeclaration;
  }
}
