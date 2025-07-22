// REBELLION 10014

package frc.RebeLib.util;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.BooleanSupplier;

/**
 * A remappable trigger that can be used to start commands based on a condition that can change at
 * runtime.
 *
 * <p>This is useful for cases where you want to use a button or other input as a trigger, but you
 * want to be able to change the condition it checks without having to create a new trigger. It is
 * both safe and neccesary to retain a reference to a RemappableTrigger object, and a new Trigger
 * object will be created for each binding.
 */
public class RemappableTrigger implements BooleanSupplier {
    private EventLoop loop;
    private BooleanSupplier mutableCondition;
    private Trigger internalTrigger;

    /**
     * Creates a remappable trigger that is active when the given condition is true.
     *
     * @param buttonLoop the event loop to use for this trigger
     * @param condition the condition to check
     */
    public RemappableTrigger(EventLoop buttonLoop, BooleanSupplier condition) {
        loop = buttonLoop;
        mapTo(condition);
    }

    /**
     * Creates a remappable trigger that is active when the given condition is true.
     *
     * @param condition the condition to check
     */
    public RemappableTrigger(BooleanSupplier condition) {
        this(CommandScheduler.getInstance().getDefaultButtonLoop(), condition);
    }

    /**
     * Creates a remappable trigger that is always inactive.
     */
    public RemappableTrigger() {
        this(() -> false);
    }

    /**
     * Maps this trigger to a new condition.
     * @param buttonLoop the event loop to use for this trigger
     * @param condition the condition to check
     */
    public void mapTo(EventLoop buttonLoop, BooleanSupplier condition) {
        mutableCondition = condition;
        internalTrigger = new Trigger(loop, () -> mutableCondition.getAsBoolean());
    }

    /**
     * Maps this trigger to a new condition.
     *
     * @param condition the condition to check
     */
    public void mapTo(BooleanSupplier condition) {
        mapTo(loop, condition);
    }

    public boolean getAsBoolean() {
        return mutableCondition.getAsBoolean();
    }

    /**
     * Starts the command when the condition changes.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onChange(Command command) {
        return internalTrigger.onChange(command);
    }

    /**
     * Starts the given command whenever the condition changes from `false` to `true`.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onTrue(Command command) {
        return internalTrigger.onTrue(command);
    }

    /**
     * Starts the given command whenever the condition changes from `true` to `false`.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onFalse(Command command) {
        return internalTrigger.onFalse(command);
    }

    /**
     * Starts the given command when the condition changes to `true` and cancels it when the condition
     * changes to `false`.
     *
     * <p>Doesn't re-start the command if it ends while the condition is still `true`. If the command
     * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger whileTrue(Command command) {
        return internalTrigger.whileTrue(command);
    }

    /**
     * Starts the given command when the condition changes to `false` and cancels it when the
     * condition changes to `true`.
     *
     * <p>Doesn't re-start the command if it ends while the condition is still `false`. If the command
     * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger whileFalse(Command command) {
        return internalTrigger.whileTrue(command);
    }

    /**
     * Toggles a command when the condition changes from `false` to `true`.
     *
     * @param command the command to toggle
     * @return this trigger, so calls can be chained
     */
    public Trigger toggleOnTrue(Command command) {
        return internalTrigger.toggleOnTrue(command);
    }

    /**
     * Toggles a command when the condition changes from `true` to `false`.
     *
     * @param command the command to toggle
     * @return this trigger, so calls can be chained
     */
    public Trigger toggleOnFalse(Command command) {
        return internalTrigger.toggleOnFalse(command);
    }

    /**
     * Composes two triggers with logical AND.
     *
     * @param trigger the condition to compose with
     * @return A trigger which is active when both component triggers are active.
     */
    public Trigger and(BooleanSupplier trigger) {
        return internalTrigger.and(trigger);
    }

    /**
     * Composes two triggers with logical OR.
     *
     * @param trigger the condition to compose with
     * @return A trigger which is active when either component trigger is active.
     */
    public Trigger or(BooleanSupplier trigger) {
        return internalTrigger.or(trigger);
    }

    /**
     * Creates a new trigger that is active when this trigger is inactive, i.e. that acts as the
     * negation of this trigger.
     *
     * @return the negated trigger
     */
    public Trigger negate() {
        return internalTrigger.negate();
    }

    /**
     * Creates a new debounced trigger from this trigger - it will become active when this trigger has
     * been active for longer than the specified period.
     *
     * @param seconds The debounce period.
     * @return The debounced trigger (rising edges debounced only)
     */
    public Trigger debounce(double seconds) {
        return debounce(seconds, Debouncer.DebounceType.kRising);
    }

    /**
     * Creates a new debounced trigger from this trigger - it will become active when this trigger has
     * been active for longer than the specified period.
     *
     * @param seconds The debounce period.
     * @param type The debounce type.
     * @return The debounced trigger.
     */
    public Trigger debounce(double seconds, Debouncer.DebounceType type) {
        return internalTrigger.debounce(seconds, type);
    }
}
