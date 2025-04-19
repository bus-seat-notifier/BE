package com.be.bus.domain.terminal.repository;

import com.be.bus.domain.terminal.entity.QTerminal;
import com.be.bus.domain.terminal.entity.Terminal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@RequiredArgsConstructor
public class TerminalQueryRepositoryImpl implements TerminalQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Terminal> findByName(String name) {
        QTerminal terminal = QTerminal.terminal;

        Terminal result = queryFactory
                .selectFrom(terminal)
                .where(terminal.name.eq(name))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
