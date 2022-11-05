FROM cimg/openjdk:11.0

ARG PROJECT_VERSION
ARG PROJECT_ARCHIVE

LABEL maintainer="Steve Hill 'sghill.dev@gmail.com'"

COPY --chown=circleci:circleci $PROJECT_ARCHIVE /buildlab/flexibuild.zip
RUN cd /buildlab && \
    unzip flexibuild.zip && \
    mkdir -p /buildlab/bin && \
    ln -s /buildlab/flexibuild-$PROJECT_VERSION/bin/flexibuild /buildlab/bin/fxb && \
    rm flexibuild.zip
