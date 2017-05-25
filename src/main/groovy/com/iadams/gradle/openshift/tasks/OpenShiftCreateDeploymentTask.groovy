/*
 * Gradle Openshift Plugin
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Iain Adams
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.iadams.gradle.openshift.tasks

import io.fabric8.openshift.api.model.DeploymentConfig
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.Input

class OpenShiftCreateDeploymentTask extends AbstractOpenshiftTask {

  @Input
  def deployment

  OpenShiftCreateDeploymentTask() {
    super('Trigger the latest deployment')
  }

  @Override
  void executeAction() {
    switch (deployment) {
      case File:
        File f = (File) getDeployment()
        client.load(f.newInputStream()).createOrReplace()
        break
      case DeploymentConfig:
        DeploymentConfig dc = (DeploymentConfig)getDeployment()
        client.deploymentConfigs().inNamespace(getNamespace()).createOrReplace(dc)
        break
      default:
        throw new GradleScriptException("Unable to create deployment with object ${getDeployment().class}")
        break
    }
  }
}
