package com.github.bpazy.mybatis.dom.converter;

import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.ConvertContext;
import com.github.bpazy.mybatis.dom.model.Mapper;
import com.github.bpazy.mybatis.util.JavaUtils;
import com.github.bpazy.mybatis.util.MapperUtils;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

/**
 * @author yanglin
 */
public class DaoMethodConverter extends ConverterAdaptor<PsiMethod> {

    @Nullable
    @Override
    public PsiMethod fromString(@Nullable @NonNls String id, ConvertContext context) {
        Mapper mapper = MapperUtils.getMapper(context.getInvocationElement());
        return JavaUtils.findMethod(context.getProject(), MapperUtils.getNamespace(mapper), id).orElse(null);
    }

}