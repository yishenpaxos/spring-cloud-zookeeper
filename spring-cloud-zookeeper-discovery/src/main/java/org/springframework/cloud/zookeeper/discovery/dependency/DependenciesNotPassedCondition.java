/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.zookeeper.discovery.dependency;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Inverse of the {@link ConditionalOnDependenciesPassed} condition. Also checks if switch for zookeeper dependencies
 * was turned on
 *
 * @author Marcin Grzejszczak
 * @since 1.0.0
 */
public class DependenciesNotPassedCondition extends DependenciesPassedCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ConditionOutcome propertiesSet = super.getMatchOutcome(context, metadata);
		if (propertiesSet.isMatch()) {
			return ConditionOutcome.inverse(propertiesSet);
		}
		Boolean dependenciesEnabled = context.getEnvironment()
				.getProperty("spring.cloud.zookeeper.dependencies.enabled", Boolean.class, false);
		if (dependenciesEnabled) {
			return ConditionOutcome.noMatch("Dependencies are defined in configuration and switch is turned on");
		}
		return ConditionOutcome.match("Dependencies are not defined in configuration and switch is turned off");
	}

}
