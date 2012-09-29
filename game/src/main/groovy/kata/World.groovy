package kata


public interface World<C extends Coords> extends Iterator<World>, MovementValidator<C> {
    void spawns(Player<C> player)
}