package com.mikelduke.java.ConsoleCommander;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConsoleCommand {
	String[] cmd();
	String desc() default "";
	String[] args() default {};
	boolean hideFromHelp() default false;
}
